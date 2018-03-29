#!/usr/bin/env bash

sed -i "s/abc/cde/g" test.txt
#bashname=`dirs`
#echo "$bashname"
#printf "adfaf"
#printf "fafd"
awk -F "#" '{print $3}' test.txt
filename="test.txt"
grep -E -o "\b[[:alpha:]]+\b" $filename | awk ' { count[$0]++ }
END{printf("%-30s%s\n","Word","Count");
for(word in count)
{printf("%-30s%s\n",word,count[word])}
}'

printf "%-30s%s" "$filename fafd"