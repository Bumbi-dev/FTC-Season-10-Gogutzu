package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.vision.VisionPortal;

import java.io.File;


public class PlayHardware extends AutonomousHardware {

    int nrRoti = 0;
    double time;
    String direction, roti;
    String[] partiRoti;

    public void init(HardwareMap hwMap) {
        /*________________________Motoare Roti____________________________*/
        motorFS = hwMap.get(DcMotor.class, "motor FataStanga");
        motorFD = hwMap.get(DcMotor.class, "motor FataDreapta");
        motorSS = hwMap.get(DcMotor.class, "motor SpateStanga");
        motorSD = hwMap.get(DcMotor.class, "motor SpateDreapta");

        motoare = new DcMotor[]{motorFS, motorFD, motorSS, motorSD};

        motorFS.setDirection(DcMotor.Direction.FORWARD);
        motorFD.setDirection(DcMotor.Direction.REVERSE);
        motorSS.setDirection(DcMotor.Direction.FORWARD);
        motorSD.setDirection(DcMotor.Direction.REVERSE);

        for (DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        for (DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        for (DcMotor motor : motoare)
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*________________________Brat____________________________*/
        motorBrat = hwMap.get(DcMotor.class, "motor Brat");

        motorBrat.setDirection(DcMotor.Direction.FORWARD);
        motorBrat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBrat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ghearaStanga = hwMap.get(Servo.class, "gheara Stanga");
        ghearaDreapta = hwMap.get(Servo.class, "gheara Dreapta");

        ghearaStanga.setDirection(Servo.Direction.FORWARD);
        ghearaDreapta.setDirection(Servo.Direction.REVERSE);

        frana();

        setLeftServoPosition(ServoPositions.CLOSE);
        setRightServoPosition(ServoPositions.CLOSE);

        capcana = hwMap.get(Servo.class, "capcana");
        capcana.setPosition(0.374);

        //____________________Senzori_____________________*/
        yoyo = hwMap.get(WebcamName.class, "Webcam 1");

        int width = 200, leftX = 0, leftY = 50, middleX = 280, middleY = 0, rightX = 400, rightY = 250;

        if(path.contains("red_backboard") || (path.contains("blue") && !path.contains("backboard"))) {
            leftX = 40;
            middleX = 430;
        }

        CSVisionProcessor visionProcessor;
        VisionPortal visionPortal;

        visionProcessor = new CSVisionProcessor(width, leftX, leftY, middleX, middleY, rightX, rightY);
        visionPortal = VisionPortal.easyCreateWithDefaults(yoyo, visionProcessor);
        CSVisionProcessor.StartingPosition startingPos = CSVisionProcessor.StartingPosition.CENTER;

        setServosPosition(ServoPositions.OPEN);

        String ogPath = path;
        boolean isSpike = false;
        while (!this.isStarted() && !this.isStopRequested()) {
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

            path = ogPath;

            try {
                telemetry.addLine("Voltage: " +ReadWriteFile.readFile(new File(ogPath + nrAutonomie + "_voltaj.txt")));
            }catch (Exception ignored) {
                telemetry.addLine("nu sa gasit");
            }

            if(path.contains("mov")) {
                telemetry.addLine("BackBoardul e pentru rosu, la albastru e opusul\n");

                if(gamepad2.back)
                    isSpike = false;
                if(gamepad2.start)
                    isSpike = true;

                if(isSpike)
                    path += "_backboard";
            }

            path += nrAutonomie + ".txt";
            telemetry.addLine(path + "");

            telemetry.update();
        }

        visionPortal.stopStreaming();

        telemetry.addLine(path + "");
        telemetry.update();

        setServosPosition(ServoPositions.CLOSE);

        roti = ReadWriteFile.readFile(new File(path));
        partiRoti = roti.trim().split("\\s+");

        telemetry.addLine(path);
        telemetry.update();

        waitForStart();

        runtime.reset();

        motorBrat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {
            nextVariables();
            proceed();
        }
    }

    private void nextVariables() {
        //TODO
        //if(nrRoti > partiRoti.length)
        //    requestOpModeStop();

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