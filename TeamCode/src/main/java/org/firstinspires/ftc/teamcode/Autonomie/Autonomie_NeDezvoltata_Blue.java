package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.File;

@Autonomous(name="Cine a facut autonomia? - ALBASTRU (record)", group="record")
public class Autonomie_NeDezvoltata_Blue extends AutonomHardware {
    String direction = "0";
    String prevDirection = "0";
    String roti = "";
    ElapsedTime runtime = new ElapsedTime();
    File fila;

    @Override
    public void init() {
        hwMap = hardwareMap;
        INIT();
    }

    @Override
    public void start() {
        path += nrAutonomie + ".txt";
        fila = new File(path);

        telemetry.addLine(path);
        telemetry.update();
        runtime.reset();
    }

    @Override
    public void loop() {
        prevDirection = direction;
        getDirection();
        proceed();
    }

    @Override
    public void stop() {
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