# KVSP-RV32I
Alternative VSP implementation using picorv32

# Installation
## RV32I toolchain

```
$ wget https://github.com/stnolting/riscv-gcc-prebuilt/releases/download/rv32i-2.0.0/riscv32-unknown-elf.gcc-10.2.0.rv32i.ilp32.newlib.tar.gz
$ sudo mkdir /opt/riscv32i
$ sudo tar -xzf riscv32-unknown-elf.gcc-10.2.0.rv32i.ilp32.newlib.tar.gz -C /opt/riscv32i/
```

## KVSP-RV32I

```
$ sudo apt install libomp5 libbinutils zlib1g libmpc3
$ tar -xvf kvsp-rv32i.tar.gz
```

# How to use
The interface of `kvsp` command is compatible with [KVSP](https://github.com/virtualsecureplatform/kvsp).
The usage of the command is shown in [this KVSP README.md](https://github.com/virtualsecureplatform/kvsp/blob/master/README.md)
