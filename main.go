package main

import (
	"fmt"
	"math/rand"
	"os"
	"strings"
	"time"
)

func main() {
	args := os.Args
	if len(args) < 2 {
		fmt.Println("Incorrect usage. Usage:\t" + args[0] + " <text>")
		return
	}

	in := strings.Join(args[1:], " ")
	text := ""

	for _, ch := range in {
		rand.Seed(time.Now().UnixNano())
		chs := string(ch) // we don't want a rune
		// thanks thwd from stackoverflow for the following ternary stuff
		c := (map[bool]string{true: strings.ToUpper(chs), false: strings.ToLower(chs)})[rand.Float32() < 0.5 == true]
		text += c
	}

	fmt.Println(text)
}
