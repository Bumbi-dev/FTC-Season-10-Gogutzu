package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ReadWriteFile;


import java.io.File;

@Autonomous(name="Milfeaza", group="Bubu")
public class SeMilfeaza extends AutonomHardware {

    File movement = new File("/storage/emulated/0/FORST/movement");
    String data = "";

    @Override
    public void runOpMode() {
        init(hardwareMap);

        String prev = "0";
        String current;

        waitForStart();

        boolean []directii = {gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left};
        String []numeDirectii = {"sus", "jos", "dreapta", "stanga"};

        while (opModeIsActive()) {

            current = "0";

            for (int i = 0; i < directii.length; i++)//gets the actual direction
                if(directii[i]) {
                    current = numeDirectii[i];
                    break;
                }

            switch (current) {
                case "sus":
                    moveStraight(0.3f);
                    break;

                case "jos":
                    moveStraight(-0.3f);
                    break;

                case "dreapta":
                    moveStrafe(0.3f);
                    break;

                case "stanga":
                    moveStrafe(-0.3f);
                    break;

                case "0" :
                    frana();
                    if(!prev.equals("0"))
                        savePosition();
                    break;

                default:
                    return;
            }

            prev = current;
        }

        ReadWriteFile.writeFile(movement, data);
    }

    private void savePosition () {
        //save all motors positions maybe
    }


}