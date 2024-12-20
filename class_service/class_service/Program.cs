using class_service.ClassApi.Factories;
using class_service.ClassApi.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Registering Singleton and Factory
builder.Services.AddSingleton<IClassFactory, ClassFactory>();
builder.Services.AddSingleton<ClassService>();

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAllOrigins",
        builder =>
        {
            builder.WithOrigins("http://localhost:8888")
                   .AllowAnyMethod()
                   .AllowAnyHeader();
        });
});

//// Configure Kestrel to listen on port 8081
//builder.WebHost.ConfigureKestrel(options =>
//{
//    options.ListenAnyIP(8082);
//});

var app = builder.Build();

// Configure the HTTP request pipeline
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

//app.UseHttpsRedirection();
//app.UseAuthorization();

// Use the CORS policy
app.UseCors("AllowAllOrigins");

app.MapControllers();

app.Run();