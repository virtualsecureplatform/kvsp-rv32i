object Main extends App{
  implicit val conf = CoreConfig()
  chisel3.Driver.execute(args, () => new Core(conf))
}