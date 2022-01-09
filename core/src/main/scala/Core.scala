import chisel3._
import chisel3.util.Cat

class Core(val conf: CoreConfig) extends Module {
    val io = IO(new Bundle {
        val rom = new RomPort(conf)
        val ram = new RamPort(conf)

        val finish = Output(Bool())
        val regs = Output(Vec(32, UInt(32.W)))
    })

    val core = Module(new picorv32)

    io.finish := core.io.trap
    io.regs := core.io.regs
    core.io.resetn := !reset.asBool()
    core.io.clk := clock

    io.rom.addr := (core.io.mem_addr >> 2)
    io.ram.addr := (core.io.mem_addr >> 2)

    core.io.mem_ready := true.B

    when(core.io.mem_instr) {
        core.io.mem_rdata := io.rom.data
    }.otherwise {
        core.io.mem_rdata := io.ram.readData
        //printf("[RAM 0x%x] => 0x%x\n", io.ram.addr, io.ram.readData);
    }

    val wstrb = Wire(UInt(4.W))
    wstrb := core.io.mem_wstrb
    io.ram.writeData := DontCare
    when(wstrb =/= 0.U(4.W)) {
        io.ram.writeEnable := true.B
        when(wstrb === 15.U(4.W)) {
            io.ram.writeData := core.io.mem_wdata
        }.elsewhen(wstrb === 12.U(4.W)) {
            io.ram.writeData := Cat(core.io.mem_wdata(31, 16), io.ram.readData(15, 0))
        }.elsewhen(wstrb === 3.U(4.W)) {
            io.ram.writeData := Cat(io.ram.readData(31, 16), core.io.mem_wdata(15, 0))
        }.elsewhen(wstrb === 8.U(4.W)) {
            io.ram.writeData := Cat(core.io.mem_wdata(31, 24), io.ram.readData(23, 0))
        }.elsewhen(wstrb === 4.U(4.W)) {
            io.ram.writeData := Cat(io.ram.readData(31, 24), core.io.mem_wdata(23, 16), io.ram.readData(15, 0))
        }.elsewhen(wstrb === 2.U(4.W)) {
            io.ram.writeData := Cat(io.ram.readData(31, 16), core.io.mem_wdata(15, 8), io.ram.readData(7, 0))
        }.elsewhen(wstrb === 1.U(4.W)) {
            io.ram.writeData := Cat(io.ram.readData(31, 8), core.io.mem_wdata(7, 0))
        }
    }.otherwise {
        io.ram.writeEnable := false.B
    }

    // unused wires
    core.io.pcpi_wr := DontCare
    core.io.pcpi_rd := DontCare
    core.io.pcpi_wait := DontCare
    core.io.pcpi_ready := DontCare
    core.io.irq := DontCare
}