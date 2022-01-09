import chisel3._

class TestTop(val d: Seq[Int], val r: Seq[Int]) extends Module {
    val io = IO(new Bundle{
        val mainReg = Output(Vec(32, UInt(16.W)))
        val finish = Output(Bool())
    })

    val conf = new CoreConfig
    val core = Module(new Core(conf))
    val rom = Module(new ExternalRom(conf, d))
    val ram = Module(new ExternalRam(conf, r))

    core.io.rom <> rom.io
    core.io.ram <> ram.io.port
    io.mainReg := core.io.regs
    io.finish := core.io.finish
}