package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.File;

@Autonomous(name="Cine a facut autonomia? - ALBASTRU (record)", group="record")
public class Autonomie_In_Dezvoltare_Blue extends AutonomHardware {
    String direction = "0";
    String prevDirection = "0";
    String roti = "";
    ElapsedTime runtime = new ElapsedTime();
    File fila;
    int prevAuto = 0;
    boolean write = false;

    @Override
    public void runOpMode() {
        init(hardwareMap);


        while (!this.isStarted() && !this.isStopRequested()) {
            if (gamepad2.right_bumper && prevAuto != 1) {
                nrAutonomie++;
                prevAuto = 1;
            }
            if (gamepad2.left_bumper && prevAuto != 2) {
                nrAutonomie--;
                prevAuto = 2;
            }
            if (nrAutonomie > 3)
                nrAutonomie = 1;
            if (nrAutonomie < 1)
                nrAutonomie = 3;
            telemetry.addLine(nrAutonomie + "");
            telemetry.update();
        }

        path += nrAutonomie + ".txt";
        fila = new File(path);

        telemetry.addLine(path);
        telemetry.update();

        waitForStart();
        runtime.reset();

        if(opModeIsActive())
            write = true;

        while(opModeIsActive()){

            prevDirection = direction;
            getDirection();
            proceed();

            if(gamepad2.ps) {
                telemetry.addLine("nu se salveaza");
                telemetry.update();
            }

            if(gamepad2.left_bumper || gamepad2.right_bumper) {
                telemetry.addLine("se salveaza");
                telemetry.update();
            }

            if(gamepad2.ps) {
                write = false;
                break;
            }
        }

        if(write)
            ReadWriteFile.writeFile(fila, roti);
    }

    private void getDirection() {
        if(gamepad1.dpad_up) {
            direction = "f";
            return;
        }
        if(gamepad1.dpad_down) {
            direction = "b";
            return;
        }
        if(gamepad1.dpad_left){
            direction = "l";
            return;
        }

        if(gamepad1.dpad_right){
            direction = "r";
            return;
        }

        if(gamepad1.left_bumper){
            direction = "tl";
            return;
        }

        if(gamepad1.right_bumper) {
            direction = "tr";
            return;
        }
        if(gamepad2.dpad_up) {
            direction = "u";
            return;
        }
        if(gamepad2.dpad_down) {
            direction = "d";
            return;
        }
        if(gamepad2.a) {
            direction = "g";
            return;
        }
        if(gamepad2.b) {
            direction = "dr";
            return;
        }
        direction = "0";
    }

    private void proceed() {
        if(prevDirection.equals(direction))
            return;

        roti += prevDirection + ' ' + runtime.seconds() + ' ';
        runtime.reset();
        moveToDirection();
    }

    private void moveToDirection() {
        switch (direction) {
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
            case "u"://up
                liftArm();
                break;
            case "d"://down
                lowerArm();
                break;
            case "g"://grab
                closeClaw();
                break;
            case "dr"://drop
                openClaw();
                break;
            default:
                frana();
                break;
        }
    }
}