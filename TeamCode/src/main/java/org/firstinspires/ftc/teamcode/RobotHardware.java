package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class RobotHardware
{

    //F-fata, S-spate, D-dreapta, S-stanga
    public DcMotor motorFS = null;
    public DcMotor motorFD = null;
    public DcMotor motorSS = null;
    public DcMotor motorSD = null;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        motorFS = hwMap.get(DcMotor.class, "motor FataStanga");
        motorFD = hwMap.get(DcMotor.class, "motor FataDreapta");
        motorSS = hwMap.get(DcMotor.class, "motor SpateStanga");
        motorSD = hwMap.get(DcMotor.class, "motor SpateDreapta");

        motorFS.setDirection(DcMotor.Direction.REVERSE);
        motorFD.setDirection(DcMotor.Direction.FORWARD);
        motorSS.setDirection(DcMotor.Direction.REVERSE);
        motorSD.setDirection(DcMotor.Direction.FORWARD);

        stop();

        motorFS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorSS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorSD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorFS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorSS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorSD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void moveStraight(float x) {
        float y = x;//acceleratie(x, "FS");

        motorFS.setPower(y); motorFD.setPower(y);
        motorSS.setPower(y); motorSD.setPower(y);
    }

    public void moveStrafe(float x) {//spre dreapta
        motorFS.setPower(-x); motorFD.setPower(x);
        motorSS.setPower(x); motorSD.setPower(-x);
    }

    public void moveDiagonal(float x, String way) {
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

    }

    public void beyBlade(float x) {//spre dreapta
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

    public void stop() {
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

    public void motorsTelemetry(Telemetry telemetrie) {

        telemetrie.addLine("FD: " + (motorFD.getPower()));
        telemetrie.addLine("FS: " + (motorFS.getPower()));
        telemetrie.addLine("SS: " + (motorSS.getPower()));
        telemetrie.addLine("SD: " + (motorSD.getPower()));
        telemetrie.update();

    }
}