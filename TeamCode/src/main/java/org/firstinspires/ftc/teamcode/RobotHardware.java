package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class RobotHardware extends LinearOpMode {

    //F-fata, S-spate, D-dreapta, S-stanga
    public DcMotor motorFS = null;
    public DcMotor motorFD = null;
    public DcMotor motorSS = null;
    public DcMotor motorSD = null;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {//init_loop ca sa se repete pana dai play

        hwMap = ahwMap;

        motorFS = hwMap.get(DcMotor.class, "motor FataStanga");
        motorFD = hwMap.get(DcMotor.class, "motor FataDreapta");
        motorSS = hwMap.get(DcMotor.class, "motor SpateStanga");
        motorSD = hwMap.get(DcMotor.class, "motor SpateDreapta");

        motorFS.setDirection(DcMotor.Direction.REVERSE);
        motorFD.setDirection(DcMotor.Direction.FORWARD);
        motorSS.setDirection(DcMotor.Direction.REVERSE);
        motorSD.setDirection(DcMotor.Direction.FORWARD);

        frana();

        motorFS.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorSS.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorSD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorFS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorSS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorSD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void moveStraight(float x) {
        /* pentru acceleratie:
           x = acceleratie(x, "FS");
        */

        motorFS.setPower(x); motorFD.setPower(x);
        motorSS.setPower(x); motorSD.setPower(x);
    }

    public void moveStrafe(float x) {//miscare laterala
        motorFS.setPower(-x); motorFD.setPower(x);
        motorSS.setPower(x); motorSD.setPower(-x);
    }

    public void moveDiagonal(float x, String way) {//miscare pe diagonala
        if(way.equals("FD")) {//x = y
            motorFS.setPower(x); motorFD.setPower(0);
            motorSS.setPower(0); motorSD.setPower(x);
        }
        else {//x = -y
            motorFS.setPower(0);  motorFD.setPower(x);
            motorSS.setPower(x);  motorSD.setPower(0);
        }
    }

    public void moveDiagonal(float x, float y) {
        motorFS.setPower(x); motorFD.setPower(y);
        motorSS.setPower(y); motorSD.setPower(x);
    }

    public void beyBlade(float x) {//rotire pe loc
        motorFS.setPower(x); motorFD.setPower(-x);
        motorSS.setPower(x); motorSD.setPower(-x);
    }

    public void doDrift(float x, String way) {
        if(way.equals("D")) {
            motorFS.setPower(x); motorFD.setPower(0);
            motorSS.setPower(x); motorSD.setPower(0);
        }
        else
            motorFS.setPower(0); motorFD.setPower(x);
            motorSS.setPower(0); motorSD.setPower(x);
    }

    public void doDrift2(float x, String way) {
        if(way.equals("F")) {
            motorFS.setPower(x); motorFD.setPower(-x);
            motorSS.setPower(0); motorSD.setPower(0);
        }
        else{
            motorFS.setPower(-x); motorFD.setPower(x);
            motorSS.setPower(0); motorSD.setPower(0);
        }
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
}