SRC=$(shell find src -name "*.java")

default: build

build: out
	javac -d out ${SRC}

out:
	mkdir out

clean:
	rm -rf out
