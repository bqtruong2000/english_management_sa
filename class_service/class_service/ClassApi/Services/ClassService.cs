using class_service.ClassApi.Collections;
using class_service.ClassApi.Factories;
using class_service.Database;
using class_service.Models;
using MongoDB.Driver;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace class_service.ClassApi.Services
{
    public class ClassService
    {
        private readonly IMongoCollection<Class> _classes;
        private readonly IClassFactory _classFactory;

        public ClassService(IConfiguration configuration, IClassFactory classFactory)
        {
            var mongoContext = MongoDbContext.GetInstance(configuration);
            _classes = mongoContext.GetCollection<Class>("classes");
            _classFactory = classFactory;

            
        }

        // CRUD Methods
        public async Task<List<Class>> GetAllProfilesAsync() =>
            await _classes.Find(_ => true).ToListAsync();


        public async Task<Class> GetClassByIdAsync(string id)
        {
            return await _classes.Find(c => c.Id == id).FirstOrDefaultAsync();
        }


        // Iterator-based Methods
        public async Task<ClassIterator> GetClassIteratorAsync()
        {
            var classList = await _classes.Find(_ => true).ToListAsync();
            return new ClassIterator(classList);
        }

        // Additional Filtering using Iterator
        public async Task<IEnumerable<Class>> GetClassesByRoomAsync(string room)
        {
            var iterator = await GetClassIteratorAsync();
            return iterator.FilterByRoom(room);
        }

    }
}
