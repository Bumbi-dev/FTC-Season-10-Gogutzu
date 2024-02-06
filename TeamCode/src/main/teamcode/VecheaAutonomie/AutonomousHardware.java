package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Constants;

import java.io.File;

public class AutonomousHardware extends LinearOpMode {
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
        OPEN,
        INIT
    }

    public void initul(HardwareMap hwMap) {
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

        setServosPosition(ServoPositions.INIT);

        capcana = hwMap.get(Servo.class, "capcana");
        capcana.setPosition(0.374);
    }
    public void moveToDirection(String direction) {
        switch (direction) {
            case "tabla":
                ridicaLaTabla();
                break;
            case "u"://up
                liftArm();
                break;
            case "d"://down
                lowerArm();
                break;
            case "grabLeft"://grab
                setLeftServoPosition(ServoPositions.CLOSE);
                break;
            case "grabRight"://drop
                setRightServoPosition(ServoPositions.CLOSE);
                break;
            case "dropLeft"://grab
                setLeftServoPosition(ServoPositions.OPEN);
                break;
            case "dropRight"://drop
                setRightServoPosition(ServoPositions.OPEN);
                break;

            case "f":
                moveForward();
                break;
            case "b":
                moveBack();
                break;
            case "l":
                strafeLeft();
                break;
            case "r":
                strafeRight();
                break;
            case "tl":
                rotateLeft();
                break;
            case "tr":
                rotateRight();
                break;

            default:
                frana();
                break;
        }
    }

    public void frana() {//se opreste
        motorFS.setPower(0);
        motorFD.setPower(0);
        motorSS.setPower(0);
        motorSD.setPower(0);

        if(motorBrat.getMode() == DcMotor.RunMode.RUN_WITHOUT_ENCODER)
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
        motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBrat.setPower(standardSpeed);
    }

    public void lowerArm() {
        motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBrat.setPower(-standardSpeed);
    }

    public void ridicaLaTabla() {
        motorBrat.setTargetPosition(-1800);
        motorBrat.setPower(0.7);
        motorBrat.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setServosPosition(ServoPositions servosPosition) {
        setLeftServoPosition(servosPosition);
        setRightServoPosition(servosPosition);
    }

    public void setLeftServoPosition(ServoPositions servoPosition) {
        float closePosition = 0;
        float openPosition = 0.25f;
        float initPosition = 0.35f;

        if(servoPosition == ServoPositions.CLOSE)
            ghearaStanga.setPosition(closePosition);
        if(servoPosition == ServoPositions.OPEN)
            ghearaStanga.setPosition(openPosition);
        if(servoPosition == ServoPositions.INIT)
            ghearaStanga.setPosition(initPosition);
    }

    public void setRightServoPosition(ServoPositions servoPosition) {
        float closePosition = 0;
        float openPosition = 0.2f;
        float initPosition = 0.30f;

        if(servoPosition == ServoPositions.CLOSE)
            ghearaDreapta.setPosition(closePosition);
        if(servoPosition == ServoPositions.OPEN)
            ghearaDreapta.setPosition(openPosition);
        if(servoPosition == ServoPositions.INIT)
            ghearaDreapta.setPosition(initPosition);
    }


    @Override
    public void runOpMode() throws InterruptedException {}
}
