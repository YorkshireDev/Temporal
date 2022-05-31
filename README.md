# Temporal
Project Temporal

---

# Summary

Temporal is an academic-geared benchmarking tool that finds all base 2 numbers that are composed entirely of even numbers in a specified timeframe using a number of CPU cores. The default configuration is 60 seconds with all of your CPU threads. 

An example of a valid base 2 number composed entirely of even numbers is 2^11 or 2048, as "2", "0", "4", and "8" are all themselves even numbers.

It's intended to be more of a fun project than anything useful in the real-world, but it does make a good benchmark for comparing CPUs :)

---

# Requirements

1. **Windows**\*, **Linux**\** or **macOS**\***
2. **Java 11** Runtime or Greater

\* Developed and Tested on Windows 10 21H2 64-bit

\** Developed and Tested on Ubuntu 22.04 LTS 64-bit

\*** Not Tested on macOS, but it should work as it's pure Java with no dependencies.

---

# Features (Current)

1. Ability to custom configure time to run and number of CPU threads used

3. A simple graphical user interface that updates in real-time with time remaining and found valid powers of two

5. An after-benchmark results window that displays total amount of powers of two processed, as well as the single largest power of two processed

6. A headless mode that runs purely in a command line interface, ideal for benchmarking on a headless operating system

---

# Usage (Graphical)

1. Double-click the `Temporal.jar` file provided, or open up a terminal and type `java -jar Temporal.jar` (no args in the terminal still runs the GUI)

2. Click the "Start" button and wait until the benchmark finishes and gives you a score

3. Optionally, change the time to run and thread-count parameters

---

# Usage (Headless)

1. Open a terminal and type `java -jar Temporal.jar x` where `x` is the time to run, for example `java -jar Temporal.jar 60` will run it for 60 seconds.

2. Optionally, add a second argument for the amount of cores. For example, `java -jar Temporal.jar 60 16` will run the program for 60 seconds with 16 CPU threads. If the second argument is left blank, your CPUs core/thread count is used

3. Wait until the benchmark finishes and gives you a score
