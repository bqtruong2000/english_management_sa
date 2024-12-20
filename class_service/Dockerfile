# Use the official .NET SDK image for building the application
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build-env
WORKDIR /app

# Copy the solution file into the container
COPY class_service.sln ./

# Copy the .csproj files to their respective locations in the container
COPY class_service/class_service.csproj ./class_service/

# Restore dependencies
RUN dotnet restore class_service.sln

# Copy the application source code
COPY . ./

# Publish the application to the 'out' directory
RUN dotnet publish -c Release -o out

# Use the official .NET Runtime image to run the application
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app

# Copy built files from the build stage
COPY --from=build-env /app/out .

## Expose the port the service listens on (customize per service)
#EXPOSE 8082

# Set environment variables for MongoDB connection (can be overridden in docker-compose)
ENV MongoDb__ConnectionString="mongodb://mongo:27017" \
    MongoDb__DatabaseName="english_management"

# Set the entry point for the container
ENTRYPOINT ["dotnet", "class_service.dll"]
