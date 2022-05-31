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

---

# Features (Upcoming)

1. A headless mode for giving the option of running the program in a command line interface

---

# Usage

1. Double-click the `Temporal.jar` file provided, or open up a terminal and type `java -jar Temporal.jar`

2. Click the "Start" button and wait until the benchmark finishes and gives you a score

3. Optionally, change the time to run and thread-count parameters
