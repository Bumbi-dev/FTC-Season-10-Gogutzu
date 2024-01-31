package org.firstinspires.ftc.LTASrob;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class RobotHardware extends LinearOpMode {

    //F-fata, S-spate, D-dreapta, S-stanga
    public DcMotor motorFS, motorFD, motorSS, motorSD;
    public DcMotor[] motoare = new DcMotor[4];

    public DcMotor motorBrat;

    public enum ArmPositions {;
        static final int HOVER_POSITION = -135;
        static final int STACK_POSITION = -380;
        static final int BACK_BOARD_POSITION = -1728;
    }

    public enum ServoPositions {
        OPEN,
        CLOSE;
    }

    public Servo ghearaStanga, ghearaDreapta;
    public Servo diana, capcana;
    HardwareMap hwMap;

    public void init(HardwareMap ahwMap) {//init_loop ca sa se repete pana dai play

        hwMap = ahwMap;

        /*________________________ Motoare Roti ____________________________*/
        motorSS = hwMap.get(DcMotor.class, "motor SpateStanga");//expansion hub 0
        motorFS = hwMap.get(DcMotor.class, "motor FataStanga");//expansion hub 1
        motorFD = hwMap.get(DcMotor.class, "motor FataDreapta");//expansion hub 2
        motorSD = hwMap.get(DcMotor.class, "motor SpateDreapta");//expansion hub 3

        motoare = new DcMotor[]{motorFS, motorFD, motorSS, motorSD};

        motorFS.setDirection(DcMotor.Direction.FORWARD);
        motorFD.setDirection(DcMotor.Direction.REVERSE);
        motorSS.setDirection(DcMotor.Direction.FORWARD);
        motorSD.setDirection(DcMotor.Direction.REVERSE);

        frana();

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        for(DcMotor motor : motoare)
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*________________________ Brat ____________________________*/
        motorBrat = hwMap.get(DcMotor.class, "motor Brat");//control hub 0

        motorBrat.setDirection(DcMotor.Direction.FORWARD);
        motorBrat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBrat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ghearaStanga = hwMap.get(Servo.class, "gheara Stanga");
        ghearaDreapta = hwMap.get(Servo.class, "gheara Dreapta");

        ghearaStanga.setDirection(Servo.Direction.FORWARD);
        ghearaDreapta.setDirection(Servo.Direction.REVERSE);

        diana = hwMap.get(Servo.class, "gheara Avion");//control hub 1
        capcana = hwMap.get(Servo.class, "capcana");//control hub 0

        diana.setPosition(0.5f);
        closeCapcana();
    }

    public void ArmToPosition(int position) {
        motorBrat.setTargetPosition(position);
        motorBrat.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void openCapcana() {capcana.setPosition(0);}

    public void closeCapcana() {capcana.setPosition(0.374);}

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

    public void frana() {
        motorFS.setPower(0); motorFD.setPower(0);
        motorSS.setPower(0); motorSD.setPower(0);
    }


    //incercare si fara acceleratie poate merge mai bine
    float acceleratie(float powerAux, String motor) {
        //powerNow se apropie de powerAux

        float powerNow;

        switch (motor){
            case "FS":
                powerNow = (float) motorFS.getPower();
                break;
            case "SD":
                powerNow = (float) motorSD.getPower();
                break;
            case "SS":
                powerNow = (float) motorSS.getPower();
                break;

            default:
                powerNow = (float) motorFD.getPower();
                break;
        }

        float varAcc = 25;//daca e 1, nu mai avem acceleratie

        if(powerNow == powerAux)
            return powerNow;

        if(powerNow < -0.25 || powerNow > 0.25)
            return powerAux;

        if(powerNow < powerAux)
            powerNow += (powerAux - powerNow) / varAcc;
        else
            powerNow -= (powerAux - powerNow) / varAcc;

        return powerNow;
    }

    public void motorsTelemetry(Telemetry telemetrie) {//afiseaza puterea motoarelor
        telemetrie.addLine("FD: " + (motorFD.getPower()));
        telemetrie.addLine("FS: " + (motorFS.getPower()));
        telemetrie.addLine("SS: " + (motorSS.getPower()));
        telemetrie.addLine("SD: " + (motorSD.getPower()));

        telemetrie.addLine();
    }

    @Override
    public void runOpMode() throws InterruptedException {}

    RobotHardware() {}
}