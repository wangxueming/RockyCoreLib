java -jar /Applications/jiagu/jiagu.jar -login winfae 1qaz@WSX
java -jar /Applications/jiagu/jiagu.jar -importsign winfae_keystore.jks Winfae123 winfae Winfae123
java -jar /Applications/jiagu/jiagu.jar -importmulpkg /Applications/jiagu/多渠道模板.txt
java -jar /Applications/jiagu/jiagu.jar -showmulpkg
java -jar /Applications/jiagu/jiagu.jar -jiagu release/winfae.apk release/ -autosign -automulpkg

