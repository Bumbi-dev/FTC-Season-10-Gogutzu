package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

import java.io.File;


public class PlayHardware extends AutonomousHardware {

    int nrRoti = 0;
    double time;
    String direction, roti;
    String[] partiRoti;
    CSVisionProcessor.StartingPosition startingPos;
    CSVisionProcessor visionProcessor;
    String ogPath;

    public void init(HardwareMap hwMap) {
        initul(hwMap);

        //____________________Senzori_____________________*/
        yoyo = hwMap.get(WebcamName.class, "Webcam 1");

        VisionPortal visionPortal;

        int width = 200, leftX = 150, leftY = 50, middleX = 430, middleY = 0, rightX = 400, rightY = 250;

        if(path.contains("red_backboard") || (path.contains("blue") && !path.contains("backboard"))) {
            leftX = 0;
            middleX = 400;
        }

        visionProcessor = new CSVisionProcessor(width, leftX, leftY, middleX, middleY, rightX, rightY);
        visionPortal = VisionPortal.easyCreateWithDefaults(yoyo, visionProcessor);
        startingPos = CSVisionProcessor.StartingPosition.CENTER;

        ogPath = path;
        boolean isBackBoardPurple = false;
        while (!this.isStarted() && !this.isStopRequested()) {
            getZone();

            path = ogPath;

            try {
                displayVoltage();
            }catch (Exception ignored) {
                telemetry.addLine("nu sa gasit");
            }

            if(path.contains("mov")) {
                telemetry.addLine("BackBoardul e pentru rosu, la albastru e opusul\n");

                if(gamepad2.back)
                    isBackBoardPurple = false;
                if(gamepad2.start)
                    isBackBoardPurple = true;

                if(isBackBoardPurple)
                    path += "_backboard";
            }

            path += nrAutonomie + ".txt";
            telemetry.addLine(path + "");

            telemetry.update();
        }
        visionPortal.stopStreaming();

        telemetry.addLine(path);
        telemetry.update();

        setServosPosition(ServoPositions.CLOSE);

        roti = ReadWriteFile.readFile(new File(path));
        partiRoti = roti.trim().split("\\s+");

        waitForStart();

        motorBrat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        runtime.reset();
        while (opModeIsActive()) {
            nextVariables();
            proceed();
        }
    }

    private void displayVoltage() {
        telemetry.addLine("Voltage: " +ReadWriteFile.readFile(new File(ogPath + nrAutonomie + "_voltaj.txt")) + '\n');

        for(int i = 1; i <= 3; i++) {
            telemetry.addLine("Voltage: " +ReadWriteFile.readFile(new File(ogPath + i + "_voltaj.txt")) + ' ');
        }
    }
    private void getZone() {
        startingPos = visionProcessor.getStartingPosition();
        telemetry.addLine(CSVisionProcessor.avgLeft + " ");
        telemetry.addLine(CSVisionProcessor.avgMiddle + " ");
        telemetry.addLine(CSVisionProcessor.avgRight + " ");
        telemetry.addData("Identified", startingPos);

        if (startingPos == CSVisionProcessor.StartingPosition.LEFT)
            nrAutonomie = 1;
        if (startingPos == CSVisionProcessor.StartingPosition.CENTER)
            nrAutonomie = 2;
        if (startingPos == CSVisionProcessor.StartingPosition.RIGHT)
            nrAutonomie = 3;

    }

    private void nextVariables() {
        direction = partiRoti[nrRoti++];
        time = Double.parseDouble(partiRoti[nrRoti++]);
    }
    private void proceed(){
        runtime.reset();
        while(runtime.seconds() < time)
            moveToDirection(direction);
    }


    @Override
    public void runOpMode() throws InterruptedException {}
}