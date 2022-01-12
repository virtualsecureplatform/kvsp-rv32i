.section .text
.global _start
_start:
	li sp, 4
	lw sp, 0(sp)
	lw a0, 0(sp)	# argc
	addi a1, sp, 4	# argv

	jal ra, main
	addi x8, a0, 0
	ebreak
