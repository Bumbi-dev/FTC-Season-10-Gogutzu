/*package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name="Play Recording", group="Bursuc")
public class SeMerge extends AutonomHardware {//se deplaseaza in functie de pozitiile din fisierul movement

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

        while(opModeIsActive())
            moveMotorsToPositions();//se misca fara delay la positiile dorite

    }

    private void moveMotorsToPositions() {
        for(DcMotor motor : motoare)
            motor.setTargetPosition(getNextPosition(motor));

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        for(DcMotor motor : motoare)
            motor.setPower(standardSpeed);

        while(!areMotorsAtPosition() && opModeIsActive()) {
            if(gamepad1.ps) {
                frana();
                stop();
            }
        }
    }

    private boolean areMotorsAtPosition() {//verifica daca motoarele au ajuns
        for(DcMotor motor : motoare)
            if(Math.abs(motor.getCurrentPosition() - motor.getTargetPosition()) >= 4)
                return false;
        return true;
    }

    private int getNextPosition(DcMotor motor) {//returneaza urmatoarea pozitie din vector
        if (currentIndex < movementParts.length) {
            int nextPosition = movementParts[currentIndex];
            currentIndex++;
            return nextPosition;
        } else {
            return motor.getCurrentPosition();//daca nu mai sunt pozitii returneaza valoarea motorului, ca sa stea pe loc
        }
    }

    private void splitPositions() {//memoreaza in vector toate pozitiile pe rand
        String[] stringParts = movement.split("\\s+"); // Split by whitespace (including newlines)
        List<Integer> tempList = new ArrayList<>();

        for (String cuv : stringParts)
            tempList.add(Integer.parseInt(cuv));

        movementParts = tempList.toArray(new Integer[0]);
    }
}
*/
