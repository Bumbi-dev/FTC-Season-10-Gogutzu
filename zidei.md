de documentat codu maine


Pentru autonomie, ca sa fie mai ascuns ca nu detectam nimica, sa ascund nr autonomiei in valorile senzorului imu
de ex sa inceapa valoarea X cu 1. (X: 1,6205643) si in rest sa fie ok.
















//mod la caterinca
boolean dublu = false;

caterinca
if(gamepad1.left_bumper)
    dublu = true;
if(gamepad1.right_bumper)
    dublu = false;

if(dublu) {
    doubleMovement();
    continue;
}

private void doubleMovement() {
motorFS.setPower(-gamepad1.left_stick_y); motorFD.setPower(-gamepad1.right_stick_y);
motorSS.setPower(-gamepad2.left_stick_y); motorSD.setPower(-gamepad2.right_stick_y);
}