package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name="SeMerge", group="Bursuc")
public class SeMerge extends AutonomHardware {

    String movement = ReadWriteFile.readFile(new File("/storage/emulated/0/FIRST/movement"));
    Integer[] movementParts;
    int currentIndex = 0;

    @Override
    public void runOpMode() {
        init(hardwareMap);

        waitForStart();

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        splitPositions();

        while(opModeIsActive()) {
            sleep(1000);
            moveMotorsToPositions();
        }
    }

    private void moveMotorsToPositions() {

        for(DcMotor motor : motoare)
            motor.setTargetPosition(getNextPosition(motor));

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        for(DcMotor motor : motoare)
            motor.setPower(standardSpeed);

        while(!areMotorsAtPosition() && opModeIsActive()) {
            int x = 0;
            for(DcMotor motor : motoare)
                x += Math.abs(motor.getCurrentPosition() - motor.getTargetPosition());

            telemetry.addLine();
            telemetry.update();
            
            if(gamepad1.ps)
                stop();
        }
    }

    private boolean areMotorsAtPosition() {
        for(DcMotor motor : motoare)
            if(motor.getCurrentPosition() != motor.getTargetPosition())
                return false;
        return true;
    }

    private int getNextPosition(DcMotor motor) {
        if (currentIndex < movementParts.length) {
            int nextPosition = movementParts[currentIndex];
            currentIndex++;
            return nextPosition;
        } else {
            return motor.getCurrentPosition(); // Return a default value when there are no more numbers
        }
    }

    private void splitPositions() {
        String[] stringParts = movement.split("\\s+"); // Split by whitespace (including newlines)
        List<Integer> tempList = new ArrayList<>();

        for (String cuv : stringParts)
            tempList.add(Integer.parseInt(cuv));

        movementParts = tempList.toArray(new Integer[0]);
    }

}