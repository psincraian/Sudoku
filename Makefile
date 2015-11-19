all:
	# Comanda per generar tot
	javac ./common/*.java ./propias/dominio/clases/*.java ./propias/dominio/clases/dlx/*.java ./propias/dominio/controladores/*.java ./propias/persistencia/*.java ./propias/presentacion/*.java main.java ./propias/Driver/*.java

clean:
	rm ./common/*.class ./propias/dominio/clases/*.class ./propias/dominio/clases/dlx/*.class ./propias/dominio/controladores/*.class ./propias/persistencia/*.class ./propias/presentacion/*.class main.class ./propias/Driver/*.class
