package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Teaka! (preload si atat!)", group="slab")
public class Autonomie_Spike extends PlayHardware {
    @Override
    public void runOpMode() {
        path += "_mov";

        init(hardwareMap);
    }
}