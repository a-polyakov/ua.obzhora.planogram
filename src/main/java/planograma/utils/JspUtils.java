package planograma.utils;

import planograma.constant.SecurityConst;
import planograma.constant.SessionConst;
import planograma.data.UserContext;
import planograma.model.SecurityModel;

import javax.servlet.http.HttpSession;

/**
 * Date: 15.03.12
 * Time: 4:18
 *
 * @author Alexandr Polyakov
 */
public class JspUtils {
	private static int maxMenuTitleLength = 28;

	public static String toMenuTitle(String title) {
		final StringBuilder res = new StringBuilder(title);
		int add = maxMenuTitleLength - res.length();
		while (add > 0) {
			res.append("&nbsp;");
			add--;
		}
		return res.toString();
	}

	public static String actionAccess(final HttpSession session, final SecurityConst code_object) {
		String result = "disabled";
		if (session != null) {
			final UserContext userContext = (UserContext) session.getAttribute(SessionConst.SESSION_USER);
			if (userContext != null) {
				if (SecurityModel.getInstance().canAccess(userContext, code_object)) {
					result = "";
				}
			}
		}
		return result;
	}
}
