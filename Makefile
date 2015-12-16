all:
	# Comanda per generar tot
	javac ./common/*.java ./propias/dominio/clases/*.java ./propias/dominio/controladores/*.java ./propias/persistencia/*.java ./propias/presentacion/*.java main.java ./propias/dominio/controladores/generator/*.java ./propias/dominio/controladores/generator/dlx/*.java

jar: all
	jar cfe Sudoku.jar main ./common/*.class ./propias/dominio/clases/*.class ./propias/dominio/controladores/*.class ./propias/persistencia/*.class ./propias/presentacion/*.class main.class ./propias/dominio/controladores/generator/*.class ./propias/dominio/controladores/generator/dlx/*.class

run:
	java main
	
clean:
	rm ./common/*.class ./propias/dominio/clases/*.class ./propias/dominio/controladores/*.class ./propias/persistencia/*.class ./propias/presentacion/*.class main.class ./propias/Driver/*.class ./propias/dominio/controladores/generator/*.class ./propias/dominio/controladores/generator/dlx/*.class
