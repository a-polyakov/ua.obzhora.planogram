package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.WaresGroupConst;
import planograma.data.UserContext;
import planograma.data.WaresGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Date: 27.04.12
 * Time: 3:47
 *
 * @author Alexandr Polyakov
 */
public class WaresGroupModel {

	private static final Logger LOG = Logger.getLogger(WaresGroupModel.class);

	private static final String Q_TREE = "SELECT" +
			" " + WaresGroupConst.CODE_GROUP_WARES + "," +
			" " + WaresGroupConst.CODE_PARENT_GROUP_WARES + "," +
			" " + WaresGroupConst.NAME + " " +
			"from " + WaresGroupConst.TABLE_NAME + " " +
			"order by " + WaresGroupConst.CODE_GROUP_WARES;

	public List<WaresGroup> tree(final UserContext userContext) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_TREE);
		final ResultSet resultSet = ps.executeQuery();
		final List<WaresGroup> rootList = new ArrayList<WaresGroup>();
		final List<WaresGroup> list = new ArrayList<WaresGroup>();
		final List<WaresGroup> lost = new ArrayList<WaresGroup>();
		while (resultSet.next()) {
			final WaresGroup item = new WaresGroup(resultSet);
			list.add(item);
			if (item.getCode_parent_group_wares() == 0) {
				rootList.add(item);
			} else {
				int index = Collections.binarySearch(list, item, new Comparator<WaresGroup>() {
					@Override
					public int compare(WaresGroup o1, WaresGroup o2) {
						return o1.getCode_group_wares().compareTo(o2.getCode_parent_group_wares());
					}
				});
				if (index >= 0) {
					final WaresGroup parent = list.get(index);
					parent.getChildren().add(item);
				} else {
					lost.add(item);
				}
			}
		}
		for (final WaresGroup item : lost) {
			int index = Collections.binarySearch(list, item, new Comparator<WaresGroup>() {
				@Override
				public int compare(WaresGroup o1, WaresGroup o2) {
					return o1.getCode_group_wares().compareTo(o2.getCode_parent_group_wares());
				}
			});
			if (index >= 0) {
				final WaresGroup parent = list.get(index);
				parent.getChildren().add(item);
			} else {
				throw new SQLException("Ошибка целостности данных");
			}
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return rootList;
	}

	private static WaresGroupModel instance = new WaresGroupModel();

	public static WaresGroupModel getInstance() {
		return instance;
	}

	private WaresGroupModel() {
	}
}
