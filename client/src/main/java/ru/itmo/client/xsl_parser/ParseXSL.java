package ru.itmo.client.xsl_parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.exceptions.CheckHumanException;
import ru.itmo.client.app.utility.CommandValidator;
import ru.itmo.common.model.HumanBeing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ParseXSL {

    public static Deque<HumanBeing> parseFromXSL() throws CheckHumanException {

        Deque<HumanBeing> humanCollection = new ArrayDeque<>();
        try {
            FileInputStream file = new FileInputStream("C://test/example-file.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                String nameCell = canNextNonNull(cellIterator);
                String soundtrackNameCell = canNextNonNull(cellIterator);
                String minutesOfWaitingCell = canNextNonNull(cellIterator);
                String impactSpeedCell = canNextNonNull(cellIterator);
                String realHeroCell = canNextNonNull(cellIterator);
                String hasToothpick = canNext(cellIterator);
                String xCell = canNextNonNull(cellIterator);
                String yCell = canNextNonNull(cellIterator);
                String moodCell = canNext(cellIterator);
                String carNameCell = canNext(cellIterator);
                String carCoolCell = canNextNonNull(cellIterator);

                HumanBeing newHuman = check(nameCell, soundtrackNameCell, minutesOfWaitingCell, impactSpeedCell,
                        realHeroCell, hasToothpick, xCell, yCell, moodCell, carNameCell, carCoolCell);

                humanCollection.add(newHuman);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return humanCollection;
    }

    private static HumanBeing check(String nameCell, String soundtrackNameCell, String minutesOfWaitingCell,
                       String impactSpeedCell, String realHeroCell, String hasToothpick, String xCell,
                              String yCell, String moodCell, String carNameCell, String carCoolCell)
            throws CheckHumanException {
        CommandValidator valid = new CommandValidator();

        HumanBeing human = new HumanBeing();

        try {
            human.setName(valid.checkNonNullFields(nameCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.setSoundtrackName(valid.checkNonNullFields(soundtrackNameCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.setMinutesOfWaiting(valid.checkLongFields(minutesOfWaitingCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.setImpactSpeed(valid.checkIntFields(impactSpeedCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.setRealHero(valid.checkNonNullBoolean(realHeroCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.setHasToothpick(valid.checkBoolean(hasToothpick));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.getCoordinates().setX(valid.checkLimitedInt(xCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.getCoordinates().setY(valid.checkLimitedFloat(yCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.setMood(valid.checkMood(moodCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            human.getCar().setCarCool(valid.checkNonNullBoolean(carCoolCell));
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            throw new CheckHumanException(e.getErrorType());
        }

        human.getCar().setCarName(carNameCell);

        return human;
    }

    private static String canNextNonNull(Iterator<Cell> cell) throws CheckHumanException {
        try {
            Cell cellValue = cell.next();
            return cellValue.getStringCellValue();
        } catch(NoSuchElementException e) {
            throw new CheckHumanException(CheckHumanException.ErrorType.EMPTY);
        }
    }

    private static String canNext(Iterator<Cell> cell) {
        try {
            Cell cellValue = cell.next();
            return cellValue.getStringCellValue();
        } catch(NoSuchElementException e) {
            return null;
        }
    }
}
