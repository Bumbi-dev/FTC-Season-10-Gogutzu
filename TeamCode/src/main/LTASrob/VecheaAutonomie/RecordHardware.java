package org.firstinspires.ftc.LTASrob.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.LTASrob.Constants;

import java.io.File;


public class RecordHardware extends LinearOpMode {

    //F-fata, S-spate, D-dreapta, S-stanga
    public DcMotor motorFS, motorFD, motorSS, motorSD, motorBrat;
    public Servo ghearaStanga, ghearaDreapta;
    public Servo capcana;

    public File fila;
    private int nrAutonomie = 2;
    private String culoareAutonomie = "_blue";
    private final float standardSpeed = Constants.standardSpeed;

    public void init(HardwareMap hwMap) {
        boolean prevPressed = false;

        while (!this.isStarted() && !this.isStopRequested()) {
            if(gamepad2.back)
                culoareAutonomie = "_blue";
            if(gamepad2.start)
                culoareAutonomie = "_red";

            if (gamepad2.right_bumper && !prevPressed)
                nrAutonomie++;
            if (gamepad2.left_bumper && !prevPressed)
                nrAutonomie--;

            if (nrAutonomie > 3)
                nrAutonomie = 3;
            if (nrAutonomie < 1)
                nrAutonomie = 1;

            prevPressed = gamepad2.left_bumper || gamepad2.right_bumper;

            String path = Constants.path;
            path += nrAutonomie + culoareAutonomie +".txt";
            
            telemetry.addLine(path);
            telemetry.update();
        }


        //fila = new File(path);
//
        //telemetry.addLine(path);
        //telemetry.update();
    }

    public void frana() {//se opreste
        motorFS.setPower(0); motorFD.setPower(0);
        motorSS.setPower(0); motorSD.setPower(0);
        motorBrat.setPower(0);
    }

    public void moveForward() {//miscare fata spate
        motorFS.setPower(standardSpeed); motorFD.setPower(standardSpeed);
        motorSS.setPower(standardSpeed); motorSD.setPower(standardSpeed);
    }
    public void moveBack() {//miscare fata spate
        motorFS.setPower(-standardSpeed); motorFD.setPower(-standardSpeed);
        motorSS.setPower(-standardSpeed); motorSD.setPower(-standardSpeed);
    }
    public void strafeLeft() {//miscare laterala
        motorFS.setPower(-standardSpeed); motorFD.setPower(standardSpeed);
        motorSS.setPower(standardSpeed); motorSD.setPower(-standardSpeed);
    }
    public void strafeRight() {//miscare laterala
        motorFS.setPower(standardSpeed); motorFD.setPower(-standardSpeed);
        motorSS.setPower(-standardSpeed); motorSD.setPower(standardSpeed);
    }
    public void rotateLeft() {//rotire pe loc
        motorFS.setPower(-standardSpeed); motorFD.setPower(standardSpeed);
        motorSS.setPower(-standardSpeed); motorSD.setPower(standardSpeed);
    }
    public void rotateRight() {//rotire pe loc
        motorFS.setPower(standardSpeed); motorFD.setPower(-standardSpeed);
        motorSS.setPower(standardSpeed); motorSD.setPower(-standardSpeed);
    }

    //brat
    public void liftArm() {
        motorBrat.setPower(standardSpeed);
    }
    public void lowerArm() {
        motorBrat.setPower(-standardSpeed);
    }

    public void closeClaw() {
        ghearaStanga.setPosition(0);
        ghearaDreapta.setPosition(0);
    }
    public void openClaw() {
        ghearaDreapta.setPosition(0.25f);
        ghearaStanga.setPosition(0.25f);
    }

    @Override
    public void runOpMode() throws InterruptedException{}
}