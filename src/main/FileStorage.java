import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    public List<Timetable> readTimetable() {
        List<Timetable> timeTableList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Timetable.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                if (!st.equals("")) {
                    String[] line = st.split(",");
                    Timetable timetable = new Timetable();
                    timetable.setTimetableId(Integer.parseInt(line[0]));
                    timetable.setWeekend(line[1]);
                    timetable.setDay(line[2]);

                    timetable.setFirstExerciseType(line[3]);
                    timetable.setFirstRemainingSeats(Integer.parseInt(line[4]));
                    timetable.setFirstPrice(Integer.parseInt(line[5]));

                    timetable.setSecondExerciseType(line[6]);
                    timetable.setSecondRemainingSeats(Integer.parseInt(line[7]));
                    timetable.setSecondPrice(Integer.parseInt(line[8]));

                    timeTableList.add(timetable);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return timeTableList;
    }

    public void writeTimetable(List<Timetable> timeTableList) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("Timetable.txt", false));
            for (Timetable tt : timeTableList) {
                writer.write(tt.getTimetableId() + "," + tt.getWeekend() + "," + tt.getDay() + "," + tt.getFirstExerciseType() + "," + tt.getFirstRemainingSeats() + "," + tt.getFirstPrice() + "," + tt.getSecondExerciseType() + "," + tt.getSecondRemainingSeats() + "," + tt.getSecondPrice());
                writer.write("\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> readCustomers() {
        List<Customer> customerList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Customers.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                if (!st.equals("")) {
                    String[] line = st.split(",");
                    Customer customer = new Customer();
                    customer.setCustomerName(line[0]);

                    List<Classes> classesList = new ArrayList<>();
                    int counter = 1;
                    try {
                        while (line[counter] != null) {
                            Classes classes = new Classes();
                            classes.setBookingId(Integer.parseInt(line[counter]));
                            classes.setTimetableId(Integer.parseInt(line[counter + 1]));
                            classes.setClassNumber(Integer.parseInt(line[counter + 2]));
                            classes.setStatus(line[counter + 3]);
                            classesList.add(classes);
                            counter = counter + 4;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // do nothing
                    }
                    customer.setClasses(classesList);
                    customerList.add(customer);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    public void writeCustomers(List<Customer> customerList) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("Customers.txt", false));
            for (Customer c : customerList) {
                writer.write(c.getCustomerName());
                if(!c.getClasses().isEmpty()) {
                    writer.write(",");
                    for(Classes classes : c.getClasses()){
                        writer.write(classes.getBookingId() + "," + classes.getTimetableId() + "," + classes.getClassNumber() + "," + classes.getStatus() + ",");
                    }
                }
                writer.write("\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ratings> readRatings() {
        List<Ratings> ratingsList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Ratings.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                if (!st.equals("")) {
                    String[] line = st.split(",");
                    Ratings ratings = new Ratings();
                    ratings.setName(line[0]);
                    ratings.setBookingId(Integer.parseInt(line[1]));
                    ratings.setRating(Integer.parseInt(line[2]));
                    ratings.setReview(line[3]);

                    ratingsList.add(ratings);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ratingsList;
    }

    public void writeRatings(List<Ratings> ratingsList) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("Ratings.txt", false));
            for (Ratings r : ratingsList) {
                writer.write(r.getName() + "," + r.getBookingId() + "," + r.getRating() + "," + r.getReview());
                writer.write("\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MonthlyReportData> readMonthlyReportData() {
        List<MonthlyReportData> monthlyReportDataList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("MonthlyReportData.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                if (!st.equals("")) {
                    String[] line = st.split(",");
                    MonthlyReportData report = new MonthlyReportData();
                    report.setTimeTableId(Integer.parseInt(line[0]));
                    report.setClassNumber(Integer.parseInt(line[1]));
                    report.setTotalRatings(Integer.parseInt(line[2]));
                    report.setTotalClasses(Integer.parseInt(line[3]));

                    monthlyReportDataList.add(report);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return monthlyReportDataList;
    }

    public void writeMonthlyReportData(List<MonthlyReportData> monthlyReportDataList) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("MonthlyReportData.txt", false));
            for (MonthlyReportData report : monthlyReportDataList) {
                writer.write(report.getTimeTableId() + "," + report.getClassNumber() + "," + report.getTotalRatings() + "," + report.getTotalClasses());
                writer.write("\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MonthlyFitnessReportData> readMonthlyFitnessReportData() {
        List<MonthlyFitnessReportData> monthlyFitnessReportData = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("MonthlyFitnessReportData.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                if (!st.equals("")) {
                    String[] line = st.split(",");
                    MonthlyFitnessReportData report = new MonthlyFitnessReportData();
                    report.setTimeTableId(Integer.parseInt(line[0]));
                    report.setFitnessType(line[1]);
                    report.setPrice(Integer.parseInt(line[2]));

                    monthlyFitnessReportData.add(report);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return monthlyFitnessReportData;
    }

    public void writeMonthlyFitnessReportData(List<MonthlyFitnessReportData> monthlyFitnessReportData) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("MonthlyFitnessReportData.txt", false));
            for (MonthlyFitnessReportData report : monthlyFitnessReportData) {
                writer.write(report.getTimeTableId() + "," + report.getFitnessType() + "," + report.getPrice());
                writer.write("\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
