package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ReadWriteFile;


import java.io.File;

@TeleOp(name="Milfeaza", group="Bursuc")
public class SeMilfeaza extends AutonomHardware {

    File movement = new File("/storage/emulated/0/FORST/movement");
    String data = "";
    String []numeDirectii = {"sus", "jos", "dreapta", "stanga"};
    String current;

    final float standardSpeed = 0.3f;
    
    @Override
    public void runOpMode() {
        init(hardwareMap);

        String prev = "0";

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.ps)
                return;

            current = "0";

            getDirection();

            telemetry.addLine(current);
            telemetry.update();

            switch (current) {
                case "sus":
                    moveStraight(standardSpeed);
                    break;

                case "jos":
                    moveStraight(-standardSpeed);
                    break;

                case "dreapta":
                    moveStrafe(standardSpeed);
                    break;

                case "stanga":
                    moveStrafe(-standardSpeed);
                    break;

                case "0" :
                    frana();
                    break;

                default://in caz ca ceva nu merge bine se opreste tot
                    return;
            }

            if(!prev.equals(current))
                savePosition();

            prev = current;
        }

        ReadWriteFile.writeFile(movement, data);
    }

    private void savePosition () {

        /*save all motors positions maybe
         *data += FS  FD
         *        SS  SD
         *data += "\n\n";
         */
    }

    private void getDirection() {
        boolean []directii = {gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left};

        for (int i = 0; i < directii.length; i++)//gets the actual direction
            if(directii[i]) {
                current = numeDirectii[i];
                break;
            }
    }
}