package org.firstinspires.ftc.LTASrob.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.LTASrob.CSVisionProcessor;
import org.firstinspires.ftc.LTASrob.Constants;
import org.firstinspires.ftc.vision.VisionPortal;

import java.io.File;


public class AutonomHardware extends LinearOpMode {

    //F-fata, S-spate, D-dreapta, S-stanga
    public DcMotor motorFS, motorFD, motorSS, motorSD, motorBrat;
    public DcMotor[] motoare = new DcMotor[4];

    public Servo ghearaStanga, ghearaDreapta;
    public Servo capcana;
    public WebcamName yoyo;
    public ElapsedTime runtime = new ElapsedTime();

    public final float standardSpeed = Constants.standardSpeed;
    public int nrAutonomie = 2;

    public String path = Constants.path;
    public File fila;

    enum ServoPositions {
         CLOSE,
         OPEN;
    }

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
        CSVisionProcessor visionProcessor;
        VisionPortal visionPortal;

        visionProcessor = new CSVisionProcessor(width, leftX, leftY, middleX, middleY, rightX, rightY);
        visionPortal = VisionPortal.easyCreateWithDefaults(yoyo, visionProcessor);
        CSVisionProcessor.StartingPosition startingPos = CSVisionProcessor.StartingPosition.CENTER;

        while (!this.isStarted() && !this.isStopRequested()) {
            startingPos = visionProcessor.getStartingPosition();
            telemetry.addLine(CSVisionProcessor.avgLeft + " ");
            telemetry.addLine(CSVisionProcessor.avgMiddle + " ");
            telemetry.addLine(CSVisionProcessor.avgRight + " ");
            telemetry.addData("Identified", startingPos);
            telemetry.update();
        }
        visionPortal.stopStreaming();

        if (startingPos == CSVisionProcessor.StartingPosition.LEFT)
            nrAutonomie = 1;
        if (startingPos == CSVisionProcessor.StartingPosition.CENTER)
            nrAutonomie = 2;
        if (startingPos == CSVisionProcessor.StartingPosition.RIGHT)
            nrAutonomie = 3;

        telemetry.addLine(nrAutonomie + "");
        telemetry.update();
    }

    public void frana() {//se opreste
        motorFS.setPower(0);
        motorFD.setPower(0);
        motorSS.setPower(0);
        motorSD.setPower(0);
        motorBrat.setPower(0);
    }

    public void moveForward() {//miscare fata spate
        motorFS.setPower(standardSpeed);
        motorFD.setPower(standardSpeed);
        motorSS.setPower(standardSpeed);
        motorSD.setPower(standardSpeed);
    }

    public void moveBack() {//miscare fata spate
        motorFS.setPower(-standardSpeed);
        motorFD.setPower(-standardSpeed);
        motorSS.setPower(-standardSpeed);
        motorSD.setPower(-standardSpeed);
    }

    public void strafeLeft() {//miscare laterala
        motorFS.setPower(-standardSpeed);
        motorFD.setPower(standardSpeed);
        motorSS.setPower(standardSpeed);
        motorSD.setPower(-standardSpeed);
    }

    public void strafeRight() {//miscare laterala
        motorFS.setPower(standardSpeed);
        motorFD.setPower(-standardSpeed);
        motorSS.setPower(-standardSpeed);
        motorSD.setPower(standardSpeed);
    }

    public void rotateLeft() {//rotire pe loc
        motorFS.setPower(-standardSpeed);
        motorFD.setPower(standardSpeed);
        motorSS.setPower(-standardSpeed);
        motorSD.setPower(standardSpeed);
    }

    public void rotateRight() {//rotire pe loc
        motorFS.setPower(standardSpeed);
        motorFD.setPower(-standardSpeed);
        motorSS.setPower(standardSpeed);
        motorSD.setPower(-standardSpeed);
    }

    //brat
    public void liftArm() {
        motorBrat.setPower(standardSpeed);
    }

    public void lowerArm() {
        motorBrat.setPower(-standardSpeed);
    }

       public void setLeftServoPosition(ServoPositions servoPosition) {
        float closePosition = 0;
        float openPosition = 0.25f;

        if(servoPosition == ServoPositions.CLOSE)
            ghearaStanga.setPosition(closePosition);
        if(servoPosition == ServoPositions.OPEN)
            ghearaStanga.setPosition(openPosition);
    }

    public void setRightServoPosition(ServoPositions servoPosition) {
        float closePosition = 0;
        float openPosition = 0.25f;

        if(servoPosition == ServoPositions.CLOSE)
            ghearaDreapta.setPosition(closePosition);
        if(servoPosition == ServoPositions.OPEN)
            ghearaDreapta.setPosition(openPosition);
    }

    @Override
    public void runOpMode() throws InterruptedException {}
}