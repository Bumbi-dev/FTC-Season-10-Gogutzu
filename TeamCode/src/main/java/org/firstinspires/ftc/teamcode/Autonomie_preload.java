package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="...", group="Bubu")
public class Autonomie_preload extends AutonomHardware {

    float nr;

    @Override
    public void runOpMode() {
        nr = 1;

        init(hardwareMap);

        telemetry.addLine(motorFS.getCurrentPosition() + " " + motorFD.getCurrentPosition() + "\n" + motorSS.getCurrentPosition() + " " + motorSD.getCurrentPosition());

        waitForStart();

        while (opModeIsActive()) {

            startThread();

            if(gamepad1.right_bumper || gamepad1.left_bumper)
                return;

            nr -= gamepad1.left_stick_y / 100;

            telemetry.addLine(nr + "");
            telemetry.update();

            if(gamepad1.a)
                goTo((int) nr);

            if(gamepad1.b)
                strafeTo((int) nr);

            if(gamepad1.y)
                goTo(0);
        }
    }

    public void goTo(int x) {
        for(DcMotor motor : motoare)
            motor.setTargetPosition(x);

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        moveStraight(0.3f);

        while(motoare[1].getCurrentPosition() != x && opModeIsActive()) {//inlocuire cu atTargetPosition pt mai multa acuratete dar mai putina viteza
            sleep(20);
        }

        frana();
    }

    public void strafeTo(int x) {
        x = -x;//pt daca ii cu plus merge spre dreapta ca intro axa xOy

        for (DcMotor motor : motoare) {
            motor.setTargetPosition(x);
            x = -x;
        }

        telemetry.addLine("se duce sanki");
        telemetry.update();

        moveStraight(0.3f);

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(!atTargetPosition(x) && opModeIsActive()) {
            for(DcMotor motor : motoare)
                telemetry.addLine(motor.getCurrentPosition() + " " + motor.getTargetPosition());
            telemetry.update();
        }

        telemetry.addLine("gata"); telemetry.update();
        frana();
    }

    private boolean atTargetPosition(int x) {
        for(DcMotor motor : motoare) {
            if (Math.abs(motor.getCurrentPosition() - x) > 2) {//valoare mai mare pt marja de eroare
                return false;
            }
            x = -x;
        }

        telemetry.addLine("aAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n\n\nagsdfasdf\bb\basdfa");
        return true;
    }

    public void toTargetPosition() {
        //sa duc un motor sau pe toate la pozitiile cerute
    }

    public void startThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        thread.start();
    }
}
