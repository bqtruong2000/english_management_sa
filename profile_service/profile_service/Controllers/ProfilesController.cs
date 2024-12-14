using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using profile_service.Models;
using profile_service.ProfileApi.Services;

namespace profile_service.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ProfilesController : ControllerBase
    {
        private readonly ProfileService _profileService;

        public ProfilesController(ProfileService profileService)
        {
            _profileService = profileService;
        }


        [HttpGet]
        public async Task<ActionResult<List<Profile>>> GetProfiles()
        {
            var profiles = await _profileService.GetAllProfilesAsync();
            return Ok(profiles);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Profile>> GetProfile(string id)
        {
            var profile = await _profileService.GetProfileByIdAsync(id);

            if (profile == null)
                return NotFound();

            return Ok(profile);
        }
    }
}
