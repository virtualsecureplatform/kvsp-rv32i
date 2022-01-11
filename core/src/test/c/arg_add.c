// #TEST {"result":42, "args":["10", "32"]}
int my_atoi(char *p) {
    int k = 0;
    while (*p) {
        k = (k << 3) + (k << 1) + (*p) - '0';
        p++;
     }
     return k;
}

int add(int a, int b) {
    return a+b;
}

int main(int argc, char **argv) {
	return add(my_atoi(argv[1]), my_atoi(argv[2]));
}
