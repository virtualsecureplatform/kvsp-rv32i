import chisel3._
import chisel3.util.Cat

class ExternalRomPort(val conf: CoreConfig) extends Bundle {
	val addr = Input(UInt(conf.romWordAddressWidth.W))
	val data = Output(UInt(conf.romWordLength.W))
}
class ExternalRom(val conf: CoreConfig, val d: Seq[Int]) extends Module {
    val io = IO(new ExternalRomPort(conf))

    val rom = VecInit(d map (x=> x.U(8.W)))
    io.data := Cat(rom(io.addr*4.U+3.U), rom(io.addr*4.U+2.U), rom(io.addr*4.U+1.U), rom(io.addr*4.U))

    //printf("ROM[0x%x] => 0x%x\n", io.addr, io.data)
}