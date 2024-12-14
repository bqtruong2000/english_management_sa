using MongoDB.Driver;

namespace profile_service.Database
{
    public sealed class MongoDbContext
    {
        private static MongoDbContext _instance;
        private static readonly object _lock = new object();

        public IMongoDatabase Database { get; private set; }

        private MongoDbContext(IConfiguration configuration)
        {
            var connectionString = configuration["MongoDB:ConnectionString"];
            var databaseName = configuration["MongoDB:DatabaseName"];

            var client = new MongoClient(connectionString);
            Database = client.GetDatabase(databaseName);
        }

        // Thread-safe singleton implementation
        public static MongoDbContext GetInstance(IConfiguration configuration)
        {
            if (_instance == null)
            {
                lock (_lock)
                {
                    if (_instance == null)
                    {
                        _instance = new MongoDbContext(configuration);
                    }
                }
            }
            return _instance;
        }

        // Provide collection access method
        public IMongoCollection<T> GetCollection<T>(string collectionName)
        {
            return Database.GetCollection<T>(collectionName);
        }
    }
}
