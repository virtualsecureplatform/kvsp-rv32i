static int fib(int n) {
  int a = 0, b = 1;
  for (int i = 0; i < n; i++) {
    int tmp = a + b;
    a = b;
    b = tmp;
  }
  return a;
}

int main(int argc, char **argv) {
  // Calculate n-th Fibonacci number.
  // n is a 1-digit number and given as command-line argument.
  return fib(argv[1][0] - '0');
}
