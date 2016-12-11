# This program sorts the big file

**To sort a large file is divided into small files.**

For start:
1. Open console 
2. Write: 
java -cp "path_to_jar" Main "path_to_input_file" "path_to_output_file" "number_of_temp_files"

number_of_temp_files - it's a special program parameter, which specifies the number of rows in the temporary files. It is used to determine the amount of RAM.

For example:
java -cp /Users/user/IdeaProjects/sort-values/target/sort-values-1.0-SNAPSHOT.jar Main /Users/user/IdeaProjects/sort-values/input.in /Users/user/IdeaProjects/sort-values/ 10000000