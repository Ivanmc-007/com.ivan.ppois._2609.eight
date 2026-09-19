package com.ivan.ppois._2609.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class FileService {

    private final String pathToFile;

    private final String FILE_SEPARATOR = "%@#@:`";

    private final String format = "| %-4s | %-20s | %-30s | %-14s | %-20s | %-12s | %-11s |\n";

    public FileService(String pathToFile) {
        this.pathToFile = pathToFile;
        init();
    }

    public void displayData() {
        displayTableHead();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            int count = 0;
            // Читаем строки, пока файл не закончится (readLine вернет null)
            String line;
            while ((line = reader.readLine()) != null) {
                if (count != 0) {
                    List<String> row = Arrays.asList(line.split(FILE_SEPARATOR, -1));
                    if (row.size() >= 7) {
                        // Вывод строки файла в консоль
                        System.out.printf(format, row.get(0), row.get(1), row.get(2), row.get(3), row.get(4),
                                row.get(5), row.get(6));
                    } else System.out.println("Строка файла повреджена, внесите корректировки");
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public void createFile() {
        Path filePath = Paths.get(pathToFile);
        try {
            // Проверяем, существует ли файл
            if (!Files.exists(filePath)) {
                // Создаем новый файл
                Files.createFile(filePath);
                Files.writeString(filePath, "0");
            } else {
                Files.deleteIfExists(filePath); // Удаляем старый файл
                Files.createFile(filePath); // Создаем новый файл
                Files.writeString(filePath, "0");
            }
        } catch (IOException e) {
            System.err.println("Не удалось создать файл: " + e.getMessage());
        }
    }

    public void addLine(String[] newLineArray) {
        int lastId;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            String firstLine = reader.readLine();
            lastId = Integer.parseInt(firstLine);
        } catch (NumberFormatException e) {
            System.err.println("Файл повреждён, данные НЕ добавлены.");
            System.err.println("NOTE: первая строка файла не является числом или число очень большое");
            return;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }
        String newRowId = String.valueOf(lastId + 1);
        newLineArray[0] = newRowId;
        replaceFirstLine(newRowId);
        String line = String.join(FILE_SEPARATOR, newLineArray);
        String newLine = String.join(FILE_SEPARATOR, line);
        addLineAtEnd(newLine);
    }

    public void editRow(String inputId, int columnNumber, String newValue) {
        inputId = inputId.trim();
        // Валидация номера колонки
        if (1 == columnNumber) {
            System.out.println("Id записи не доступен для изменения");
            return;
        }
        if (columnNumber < 1 || columnNumber > 7) {
            System.out.println("Номер колонки должен быть в диапазоне от 1 до 7.");
        }
        File originalFile = new File(pathToFile);
        File tempFile = new File("temp_" + pathToFile); // Временный файл

        try (
                BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            // Читаем и оставляем без изменений первую строку
            String firstLine = reader.readLine();
            if (firstLine != null) {
                writer.write(firstLine);
                writer.newLine();
            }

            String currentLine;
            // Обрабатываем последующие строки данных
            while ((currentLine = reader.readLine()) != null) {
                // Если строка пустая, просто переносим её
                if (currentLine.trim().isEmpty()) {
                    writer.write(currentLine);
                    writer.newLine();
                    continue;
                }

                // Разделяем строку по сепаратору.
                String[] columns = currentLine.split(FILE_SEPARATOR, -1);

                // Первая колонка — это id. Проверяем на совпадение
                if (columns.length > 0 && columns[0].equals(inputId)) {
                    // Проверяем, существует ли запрашиваемая колонка в данной строке
                    int targetIndex = columnNumber - 1;
                    if (targetIndex < columns.length) {
                        columns[targetIndex] = newValue;
                        // Собираем строку обратно с разделителями
                        currentLine = String.join(FILE_SEPARATOR, columns);
                    }
                }
                // Записываем строку (измененную или оригинальную) во вспомогательный файл
                writer.write(currentLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при замене строки: " + e.getMessage());
            return;
        }
        // 4. Заменяем оригинальный файл обновленным временным
        try {
            Files.move(tempFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Не удалось обновить файл: " + e.getMessage());
        }
    }

    private void replaceFirstLine(String newFirstLine) {
        File originalFile = new File(pathToFile);
        File tempFile = new File("temp_" + pathToFile);
        try (
                BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            // 1. Читаем самую первую строку из оригинального файла
            String firstLine = reader.readLine();
            // 2. Если файл вообще не был пустым, записываем в начало нашу новую строку
            if (firstLine != null) {
                writer.write(newFirstLine);
                writer.newLine();
            }
            // 3. Копируем все оставшиеся строки (со 2-й и до конца) без изменений
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Ошибка при замене строки: " + e.getMessage());
            return;
        }
        // 4. Заменяем оригинальный файл обновленным временным
        try {
            Files.move(tempFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Не удалось обновить файл: " + e.getMessage());
        }
    }

    private void addLineAtEnd(String newLine) {
        try {
            Files.writeString(Paths.get(pathToFile), newLine, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void deleteLineById(String targetId) {
        File inputFile = new File(pathToFile);
        File tempFile = new File("temp_" + pathToFile); // Временный файл

        // try-with-resources автоматически закроет оба потока
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            int count = 0;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (count != 0) {
                    // Извлекаем ID (все символы до первого разделителя, например, запятой)
                    String trimmed = currentLine.trim();
                    String currentId = trimmed.split(FILE_SEPARATOR)[0]; // разделение по запятой или пробелу
                    // Если ID совпал, просто пропускаем эту строку (не пишем во временный файл)
                    if (currentId.equals(targetId)) {
                        continue;
                    }
                    writer.write(currentLine);
                    writer.newLine(); // Перенос строки
                } else {
                    writer.write(currentLine);
                    writer.newLine(); // Перенос строки
                }
                count++;
            }

        } catch (IOException e) {
            System.out.println("Непредвиденная ошибка: " + e.getMessage());
            return;
        }

        // Заменяем оригинальный файл временным
        try {
            Files.move(tempFile.toPath(), inputFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.out.println("Не удалось обновить файл.");
        }
    }

    public void displayDataGroupSort() {
        List<List<String>> rowList = this.getRowListSorted();
        displayTableHead();
        rowList.forEach(row -> {
            // Вывод строки в консоль
            System.out.printf(format, row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5),
                    row.get(6));
        });
    }

    public void displayMaxForColumn(int columnIndex) {
        displayTableHead();
        List<List<String>> rowList = this.getRowList();
        OptionalLong maxVal = rowList.stream()
                .map(row -> tryParseLong(row.get(columnIndex)))
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .max();
        List<List<String>> result = new ArrayList<>();
        if (maxVal.isPresent()) {
            long max = maxVal.getAsLong();
            result = rowList.stream()
                    .filter(row -> {
                        Long num = tryParseLong(row.get(columnIndex));
                        return num != null && num == max;
                    })
                    .toList();
        }
        // Вывести результат
        displayTableBody(result);
    }

    public void displayMinForColumn(int columnIndex) {
        displayTableHead();
        List<List<String>> rowList = this.getRowList();
        OptionalLong minVal = rowList.stream()
                .map(row -> tryParseLong(row.get(columnIndex)))
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .min();
        List<List<String>> result = new ArrayList<>();
        if (minVal.isPresent()) {
            long min = minVal.getAsLong();
            result = rowList.stream()
                    .filter(row -> {
                        Long num = tryParseLong(row.get(columnIndex));
                        return num != null && num == min;
                    })
                    .toList();
        }
        // Вывести результат
        displayTableBody(result);
    }

    private void init() {
        Path filePath = Paths.get(pathToFile);
        try {
            // Проверяем, существует ли файл
            if (!Files.exists(filePath)) {
                // Создаем новый файл
                Files.createFile(filePath);
                Files.writeString(filePath, "0"); // держатель id
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось инициализировать файл: " + e.getMessage());
        }
    }

    private void displayTableHead() {
        // Номера колонок
        String[] parts = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        System.out.printf(format, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        // Шапка таблицы
        parts = new String[]{"id", "Команда", "Имя игрока", "Номер игрока", "Год рождения игрока", "Рост игрока",
                "Вес игрока"};
        System.out.printf(format, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        // Линия
        System.out.println("-".repeat(133));
    }

    private void displayTableBody(List<List<String>> rowList) {
        rowList.forEach(row -> {
            // Вывод строки в консоль
            System.out.printf(format, row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5),
                    row.get(6));
        });
    }

    private List<List<String>> getRowList() {
        List<List<String>> rowList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            int count = 0;
            // Читаем строки, пока файл не закончится (readLine вернет null)
            String line;
            while ((line = reader.readLine()) != null) {
                if (count != 0) {
                    List<String> row = Arrays.asList(line.split(FILE_SEPARATOR, -1));
                    if (row.size() >= 7) {
                        rowList.add(row);
                    }
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return rowList;
    }

    private List<List<String>> getRowListSorted() {
        List<List<String>> rowList = getRowList();
        // Первичный компоратор по первичному ключу (без учёта регистра)
        Comparator<List<String>> firstKeyComparator = Comparator.comparing(
                row -> row.get(1), String.CASE_INSENSITIVE_ORDER);
        // Вторичный компаратор по вторичному ключу
        Comparator<List<String>> secondKeyComparator = (row1, row2) -> {
            String s1 = row1.get(6);
            String s2 = row2.get(6);
            Long n1 = tryParseLong(s1);
            Long n2 = tryParseLong(s2);
            // Если оба числа
            if (n1 != null && n2 != null) {
                return Long.compare(n1, n2);
            }
            // Первое число, второе строка -> число идет вверх (-1)
            if (n1 != null) return -1;
            // Первое строка, второе число -> строка идет вниз (1)
            if (n2 != null) return 1;
            // Оба значения - строки -> сортируем лексикографически
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        };
        return rowList.stream()
                .sorted(firstKeyComparator.thenComparing(secondKeyComparator))
                .collect(Collectors.toList());
    }

    private static Long tryParseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
