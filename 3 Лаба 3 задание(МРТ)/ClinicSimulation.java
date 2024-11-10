package Lab3;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClinicSimulation {
    public static volatile boolean noMorePatients = false;
    private static final BlockingQueue<Patient> patientQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Patient> mriQueue = new ArrayBlockingQueue<>(1);
    private static int maxQueueLength = 0;
    private static int patientIdCounter = 1;

    public static void main(String[] args) {
        Doctor doctor = new Doctor(patientQueue, mriQueue);
        MRI mri = new MRI(mriQueue);

        Thread doctorThread = new Thread(doctor);
        Thread mriThread = new Thread(mri);

        doctorThread.start();
        mriThread.start();

        Thread patientGenerator = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int patientId;
                    synchronized (ClinicSimulation.class) {
                        patientId = patientIdCounter++;
                    }
                    Patient patient = new Patient(patientId);
                    System.out.println("Пациент " + patientId + " пришел и встал в очередь");
                    patientQueue.put(patient);
                    synchronized (ClinicSimulation.class) {
                        if (patientQueue.size() > maxQueueLength) {
                            maxQueueLength = patientQueue.size();
                        }
                    }
                    Thread.sleep(patientArrivalInterval());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                noMorePatients = true;
            }
        });

        patientGenerator.start();

        try {
            Thread.sleep(simulationTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        patientGenerator.interrupt();


        try {
            doctorThread.join();
            mriThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Максимальная длина очереди: " + maxQueueLength);
    }

    private static int patientArrivalInterval() {
        return new Random().nextInt(100) + 200;
    }

    private static int simulationTime() {
        return 1000;
    }
}
