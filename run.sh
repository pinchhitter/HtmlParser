javac -cp ./lib/*:. cdac/in/email/Parser.java
java -cp ./lib/*:. cdac.in.email.Parser  -u 'https://kvsangathan.nic.in/about-kvs/directories/kvs-directory?shs_term_node_tid_depth=All&field_kv_directory_tid=&field_state_directory_tid=All' -w KV > all-kendiyavidyal.csv
java -cp ./lib/*:. cdac.in.email.Parser  -u 'http://www.sahodayaschools.org/list_of_sahodaya.php' -w SAH > sahodaya.csv
