package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

//IF YOU WANT TO CANCEL PRESS PS

@Autonomous(name="RECORD AUTONOMIE", group="record")
public class RECORD extends RecordHardware {
    String direction = "0";
    String prevDirection = "0";
    String roti = "";
    ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() {
        init(hardwareMap);

        waitForStart();

        if(isStopRequested()) return;

        runtime.reset();
        while(opModeIsActive()){
            prevDirection = direction;
            getDirection();
            proceed();

            if(gamepad2.ps) {
                ReadWriteFile.writeFile(fila, roti);
                requestOpModeStop();
            }
        }
    }

    private void getDirection() {
        if (gamepad2.y) {
            direction = "tabla";
            return;
        }
        if(gamepad2.dpad_down) {
            direction = "u";
            return;
        }
        if(gamepad2.dpad_up) {
            direction = "d";
            return;
        }
        if(gamepad2.left_trigger > 0.3) {
            direction = "grabLeft";
            return;
        }
        if(gamepad2.right_trigger > 0.3) {
            direction = "grabRight";
            return;
        }
        if(gamepad2.left_bumper) {
            direction = "dropLeft";
            return;
        }
        if(gamepad2.right_bumper) {
            direction = "dropRight";
            return;
        }

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
        direction = "0";
    }

    private void proceed() {
        if(prevDirection.equals(direction))
            return;

        roti += prevDirection + ' ' + runtime.seconds() + ' ';
        runtime.reset();
        moveToDirection(direction);
    }
}