.PHONY: build install-dist run-dist clean

# Основная цель по умолчанию
all: run-dist

# Сборка проекта
build:
	./gradlew clean build

# Установка дистрибутива
install-dist:
	./gradlew installDist

# Запуск собранного приложения (зависит от install-dist)
run-dist: install-dist
	./app/build/install/app/bin/app

# Очистка проекта
clean:
	./gradlew clean

# Полная пересборка и запуск
rebuild: clean build install-dist run-dist

# Только сборка и установка (без запуска)
dist: build install-dist