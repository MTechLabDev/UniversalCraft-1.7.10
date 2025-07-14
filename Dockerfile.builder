# Используем Zulu JDK 8
FROM azul/zulu-openjdk:8

# Устанавливаем git и unzip для ForgeGradle
RUN apt-get update \
 && apt-get install -y git unzip \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /workspace

# Копируем весь проект в /workspace
COPY . .

# Даём права на запуск
RUN chmod +x gradlew

# По умолчанию при старте контейнера запускаем сборку
ENTRYPOINT ["sh","-c","./gradlew buildServer --no-daemon && ./gradlew prepareClient --no-daemon"]
