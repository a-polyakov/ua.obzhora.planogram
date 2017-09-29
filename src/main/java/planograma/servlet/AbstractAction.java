package planograma.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.SessionConst;
import planograma.data.UserContext;
import planograma.exception.InvalidLoginOrPassword;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.SecurityModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Date: 12.02.12
 * Time: 0:40
 *
 * @author Alexandr Polyakov
 */
public abstract class AbstractAction extends HttpServlet {
	public static final String REQUEST_DATA = "data";

	protected Gson gson;
	private SecurityModel securityModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		final GsonBuilder gsonbuilder = new GsonBuilder();
		gson = gsonbuilder.create();
		securityModel = SecurityModel.getInstance();
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		long time = System.currentTimeMillis();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		final Writer writer = response.getWriter();
		final HttpSession session = request.getSession(true);
		try {
			final JsonElement requestData = gson.fromJson(request.getParameter(REQUEST_DATA), JsonElement.class);
			final JsonObject responseData = execute(session, requestData);
			response.setContentType("application/json");
			if (responseData != null)
				writer.write(responseData.toString());
		} catch (UnauthorizedException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("text/plain");
			writer.write(e.getMessage());
			getLog().error("Error doPost", e);
		} catch (SQLException e) {
			int errorCode = e.getErrorCode();
			if (errorCode == 17008) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("text/plain");
				writer.write(e.getMessage());
				session.removeAttribute(SessionConst.SESSION_USER);
			} else {
				if (errorCode == 1017) {
					e = InvalidLoginOrPassword.getInstance();
				}
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				writer.write(e.getMessage());
			}
			getLog().error("Error doPost", e);
		} catch (NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			e.printStackTrace(new PrintWriter(writer));
			getLog().error("Error doPost", e);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			writer.write(e.getMessage());
			getLog().error("Error doPost", e);
		} finally {
			rollback(session);
			time = System.currentTimeMillis() - time;
			getLog().debug(time + " ms");
		}
	}

	public static UserContext getUserContext(final HttpSession session) throws UnauthorizedException {
		if (session != null) {
			final UserContext userContext = (UserContext) session.getAttribute(SessionConst.SESSION_USER);
			if (userContext != null)
				return userContext;
		}
		throw UnauthorizedException.getInstance();
	}

	public void checkAccess(final UserContext userContext, SecurityConst code_object) throws NotAccessException {
		if (!securityModel.canAccess(userContext, code_object))
			throw NotAccessException.getInstance();
	}

	private void rollback(final HttpSession session) {
		UserContext userContext = null;
		try {
			userContext = (UserContext) session.getAttribute(SessionConst.SESSION_USER);
		} catch (IllegalStateException e) {
		}
		if (userContext != null) {
			final Connection connection = userContext.getConnection();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					getLog().error("Error rollback", e);
				}
			}
		}
	}

	protected static void commit(final UserContext userContext) throws SQLException {
		final Connection connection = userContext.getConnection();
		if (connection != null) {
			connection.commit();
		}
	}

	protected Logger getLog() {
		return Logger.getLogger(this.getClass());
	}

	protected abstract JsonObject execute(final HttpSession session, final JsonElement requestData) throws Exception;
}
