using profile_service.ProfileApi.Factories;
using profile_service.ProfileApi.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Registering Singleton and Factory
builder.Services.AddSingleton<IProfileFactory, ProfileFactory>();
builder.Services.AddSingleton<ProfileService>();

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

var app = builder.Build();

// Configure the HTTP request pipeline
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.UseAuthorization();

// Use the CORS policy
app.UseCors("AllowAllOrigins");

app.MapControllers();

app.Run();
