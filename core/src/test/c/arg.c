int my_atoi(char *p) {
    int k = 0;
    while (*p) {
        k = (k << 3) + (k << 1) + (*p) - '0';
        p++;
     }
     return k;
}

int main(int argc, char **argv) {
	return my_atoi(argv[1]);
}
