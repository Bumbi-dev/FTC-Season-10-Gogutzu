package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Base64;


public class AutonomHardware extends LinearOpMode {

    //F-fata, S-spate, D-dreapta, S-stanga
    public DcMotor motorFS = null;
    public DcMotor motorFD = null;
    public DcMotor motorSS = null;
    public DcMotor motorSD = null;

    public DcMotor[] motoare = new DcMotor[4];

    public BHI260IMU imu = null;
    HardwareMap hwMap = null;

    public final float standardSpeed = 0.3f;

    public void init(HardwareMap ahwMap) {//init_loop ca sa se repete pana dai play

        hwMap = ahwMap;

        /*________________________Motoare Roti____________________________*/
        motorFS = hwMap.get(DcMotor.class, "motor FataStanga");
        motorFD = hwMap.get(DcMotor.class, "motor FataDreapta");
        motorSS = hwMap.get(DcMotor.class, "motor SpateStanga");
        motorSD = hwMap.get(DcMotor.class, "motor SpateDreapta");

        motoare = new DcMotor[]{motorFS, motorFD, motorSS, motorSD};

        motorFS.setDirection(DcMotor.Direction.REVERSE);
        motorFD.setDirection(DcMotor.Direction.FORWARD);
        motorSS.setDirection(DcMotor.Direction.REVERSE);
        motorSD.setDirection(DcMotor.Direction.FORWARD);

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        for(DcMotor motor : motoare)
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frana();

        //____________________Senzori_____________________*/
        imu = hwMap.get(BHI260IMU.class, "imu");
    }

    public void frana() {//se opreste
        motorFS.setPower(0); motorFD.setPower(0);
        motorSS.setPower(0); motorSD.setPower(0);
    }

    public void moveStraight(float x) {//miscare fata spate
        motorFS.setPower(x); motorFD.setPower(x);
        motorSS.setPower(x); motorSD.setPower(x);
    }

    public void moveStrafe(float x) {//miscare laterala
        motorFS.setPower(x); motorFD.setPower(-x);
        motorSS.setPower(-x); motorSD.setPower(x);
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

    public void moveDiagonal(float x, float y) {//se misca pe diagonala
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



    public void motorsPowerTelemetry(Telemetry telemetrie) {//afiseaza puterea motoarelor
        telemetrie.addLine("FD: " + motorFD.getPower());
        telemetrie.addLine("FS: " + motorFS.getPower());
        telemetrie.addLine("SS: " + motorSS.getPower());
        telemetrie.addLine("SD: " + motorSD.getPower());

        telemetrie.addLine();
    }

    public void motorsPositionTelemetry(Telemetry telemetrie) {//afiseaza puterea motoarelor
        telemetrie.addLine("FD: " + motorFD.getCurrentPosition());
        telemetrie.addLine("FS: " + motorFS.getCurrentPosition());
        telemetrie.addLine("SS: " + motorSS.getCurrentPosition());
        telemetrie.addLine("SD: " + motorSD.getCurrentPosition());

        telemetrie.addLine();
    }

    @Override
    public void runOpMode() throws InterruptedException {}
}