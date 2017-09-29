package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.constant.VerificationConst;
import planograma.constant.data.SectorConst;
import planograma.data.Sector;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * Проверка параметров зала: высоты, ширины, длина должны быть больше 1000мм
 * Date: 09.01.13
 * Time: 14:45
 *
 * @author Alexandr Polyakov
 */
public class SectorMinDimensionsValidation {
	/**
	 * Проверка параметров зала: высоты, ширины, длина должны быть больше 1000мм
	 *
	 * @param fieldExceptionList список ошибок
	 * @param sector             зал
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final Sector sector) {
		if (sector.getHeight() < VerificationConst.MIN_SECTOR_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.SECTOR_HEIGHT_TOO_LITTLE(), Sector.class, 0, sector.getCode_sector(), SectorConst.HEIGHT));
		}
		if (sector.getWidth() < VerificationConst.MIN_SECTOR_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.SECTOR_WIDTH_TOO_LITTLE(), Sector.class, 0, sector.getCode_sector(), SectorConst.WIDTH));
		}
		if (sector.getHeight() < VerificationConst.MIN_SECTOR_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.SECTOR_LENGTH_TOO_LITTLE(), Sector.class, 0, sector.getCode_sector(), SectorConst.LENGTH));
		}
	}
}
