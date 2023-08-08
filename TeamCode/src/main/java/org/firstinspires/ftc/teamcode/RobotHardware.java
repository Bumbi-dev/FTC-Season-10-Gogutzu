package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


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

        motorFS.setDirection(DcMotor.Direction.FORWARD);
        motorFD.setDirection(DcMotor.Direction.REVERSE);
        motorSS.setDirection(DcMotor.Direction.FORWARD);
        motorSD.setDirection(DcMotor.Direction.REVERSE);

        stop();

        motorFS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorSS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorSD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveStraight(float x) {
        float y = -x;//acceleratie(x, "FS");

        motorFS.setPower(y); motorFD.setPower(y);
        motorSS.setPower(y); motorSD.setPower(y);
    }

    public void moveStrafe(float x) {//spre dreapta
        motorFS.setPower(x); motorFD.setPower(-x);
        motorSS.setPower(-x); motorSD.setPower(x);
    }

    public void moveDiagonal(float x, String way) {
        if(way.equals("FD")) {//x = y
            motorFS.setPower(0); motorSS.setPower(x);
            motorFD.setPower(x); motorSD.setPower(0);
        }
        else {//x = -y
            motorFS.setPower(x);  motorFD.setPower(0);
            motorSS.setPower(0);  motorSD.setPower(x);
        }
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


}