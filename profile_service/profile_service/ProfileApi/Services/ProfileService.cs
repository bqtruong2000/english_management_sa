using MongoDB.Driver;
using profile_service.Database;
using profile_service.Models;
using profile_service.ProfileApi.Collections;
using profile_service.ProfileApi.Factories;

namespace profile_service.ProfileApi.Services
{
    public class ProfileService
    {
        private readonly IMongoCollection<Profile> _profiles;
        private readonly IProfileFactory _profileFactory;

        public ProfileService(IConfiguration configuration, IProfileFactory profileFactory)
        {
            var mongoContext = MongoDbContext.GetInstance(configuration);
            _profiles = mongoContext.GetCollection<Profile>("profiles");
            _profileFactory = profileFactory;
        }

        // Factory Method for Profile Creation
        public async Task<Profile> CreateProfileByTypeAsync(string type)
        {
            var profile = _profileFactory.CreateProfile(type);
            await _profiles.InsertOneAsync(profile);
            return profile;
        }


        // Existing CRUD methods remain the same
        public async Task<List<Profile>> GetAllProfilesAsync() =>
            await _profiles.Find(_ => true).ToListAsync();

        public async Task<Profile> GetProfileByIdAsync(string id) =>
            await _profiles.Find(p => p._id == id).FirstOrDefaultAsync();

        public async Task UpdateProfileAsync(string id, Profile profile)
        {
            await _profiles.ReplaceOneAsync(p => p._id == id, profile);
        }

        public async Task DeleteProfileAsync(string id) =>
            await _profiles.DeleteOneAsync(p => p._id == id);
    }
}
